//
//  PlatformSelect.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 3/16/21.
//

import SwiftUI
import core

struct PlatformSelect: View {
    
    @ObservedObject var platformSelectViewModel = PlatformSelectViewModel()
    
    var navigateForward: () -> Void
    
    
    init(navigateForward: @escaping () -> Void) {
        self.navigateForward = navigateForward
        self.platformSelectViewModel.getPlatforms()
    }
    
   
    var body: some View {
        ZStack {
            VStack(alignment: .leading){
                HeadingContent()
                PlatformList(platformList: platformSelectViewModel.platformList) { platform,isOwned in
                    platformSelectViewModel.setPlatformAsFavorite(platform: platform, isOwned: isOwned)
                }
            }.padding()
            PlatformSelectionDone(ownedPlatformCount: platformSelectViewModel.getOwnedPlatformCount(), navigateForward: navigateForward)
        }
    }
}


struct PlatformSelectionDone: View {
    var ownedPlatformCount: Int
    var navigateForward: () -> Void
    
    var body: some View {
        if (ownedPlatformCount > 0) {
            VStack{
                Button(action: {
                    navigateForward()
                }) {
                    Text("Done")
                        .padding(.horizontal,30)
                        .padding(.vertical,10)
                        .background(Color("Primary"))
                        .font(.title3)
                        .foregroundColor(Color("OnPrimary"))
                        .cornerRadius(10.0)
                }.padding()
            }
            .frame(minWidth: 0,
                    maxWidth: .infinity,
                    minHeight: 0,
                    maxHeight: .infinity,
                    alignment: .bottomTrailing)
        }
    }
}

struct PlatformSelectionDone_Preview: PreviewProvider {
    static var previews: some View {
        PlatformSelectionDone(ownedPlatformCount: 1, navigateForward:{})
    }
}

struct PlatformList: View {
    let layout = [GridItem(.flexible(minimum: 80)), GridItem(.flexible(minimum: 80))]
    
    var platformList: [Platform]
    var onTapItem: (Platform, Bool) -> Void
    
    var body: some View {
        ScrollView {
            LazyVGrid(columns: layout, spacing: 20) {
                ForEach(platformList, id: \.self) { item in
                    PlatformListIem(item: item, onTap: onTapItem)
                }
            }
        }.padding(.top)
    }
}

struct PlatformListIem: View {
    var item: Platform
    var onTap: (Platform, Bool) -> Void
    
    
    var body: some View {
        let borderColor = item.isOwned == true ? Color("Primary") : Color("Background")
        
        VStack {
            RemoteImage1(url: "https://images.igdb.com/igdb/image/upload/t_720p/\(item.logoUrl).png")
                       .aspectRatio(contentMode: .fit)
                       .frame(width: 100, height: 100)
            Text(item.name)
                .font(.subheadline)
                .foregroundColor(Color("OnSurface")).lineLimit(1)
        }.onTapGesture {
            let isOwned = item.isOwned == true
            onTap(item, !isOwned)
        }.padding()
        .background(Color("Surface"))
        .padding()
        .border(borderColor, width: 5)
    }
}

struct PlatformList_Preview: PreviewProvider {
    static var previews: some View {
        PlatformList(platformList: [Platform(id: 1, slug: "Slug", name: "Name", logoHeight: 1080, logoWidth: 1080, logoUrl: "pleu", isOwned: false)]) { _, _ in
            
        }
    }
}

struct HeadingContent: View {
    var body: some View {
        Text("Owned Platforms")
            .font(.title)
            .fontWeight(.bold)
            .foregroundColor(Color("OnBackground"))
        Text("We will use these platforms to tailor your search results")
            .font(.subheadline)
            .foregroundColor(Color("OnBackground"))
    }
}


struct RemoteImage1: View {
    private enum LoadState {
        case loading, success, failure
    }

    private class Loader: ObservableObject {
        var data = Data()
        var state = LoadState.loading

        init(url: String) {
            guard let parsedURL = URL(string: url) else {
                fatalError("Invalid URL: \(url)")
            }

            URLSession.shared.dataTask(with: parsedURL) { data, response, error in
                if let data = data, data.count > 0 {
                    self.data = data
                    self.state = .success
                } else {
                    self.state = .failure
                }

                DispatchQueue.main.async {
                    self.objectWillChange.send()
                }
            }.resume()
        }
    }

    @StateObject private var loader: Loader
    var loading: Image
    var failure: Image

    var body: some View {
        selectImage()
            .resizable()
    }

    init(url: String, loading: Image = Image(systemName: "photo"), failure: Image = Image(systemName: "multiply.circle")) {
        _loader = StateObject(wrappedValue: Loader(url: url))
        self.loading = loading
        self.failure = failure
    }

    private func selectImage() -> Image {
        switch loader.state {
        case .loading:
            return loading
        case .failure:
            return failure
        default:
            if let image = UIImage(data: loader.data) {
                return Image(uiImage: image)
            } else {
                return failure
            }
        }
    }
}
