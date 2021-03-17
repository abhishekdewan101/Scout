//
//  PlatformSelect.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 3/16/21.
//

import SwiftUI

struct PlatformSelect: View {
    
    @ObservedObject var platformSelectViewModel = PlatformSelectViewModel()
    
    init() {
        platformSelectViewModel.getPlatforms()
    }
    
    let layout = [GridItem(.flexible(minimum: 80)), GridItem(.flexible(minimum: 80))]

    var body: some View {
        VStack{
            Text("Owned Platforms").fontWeight(.bold)
            Text("We will use these platforms to tailor your search results").padding(.bottom)
            ScrollView {
                LazyVGrid(columns: layout, spacing: 20) {
                    ForEach(platformSelectViewModel.platformList, id: \.self) { item in
                        VStack {
                            if (item.isFavorite == true) {
                                Text("Owned")
                            }
                            RemoteImage(url: "https://images.igdb.com/igdb/image/upload/t_720p/\(item.logoUrl).png")
                                       .aspectRatio(contentMode: .fit)
                                       .frame(width: 80)
                            Text(item.name).bold()
                        }.onTapGesture {
                            platformSelectViewModel.setPlatformAsFavorite(platform: item, isFavorite: !(item.isFavorite == true))
                        }
                    }
                }.padding(.horizontal)
            }
        }
        
    }
}

struct PlatformSelect_Previews: PreviewProvider {
    static var previews: some View {
        PlatformSelect()
    }
}


struct RemoteImage: View {
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
