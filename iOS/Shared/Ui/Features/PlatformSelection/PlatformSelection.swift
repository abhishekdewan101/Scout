//
//  PlatformSelection.swift
//  GameTracker (iOS)
//
//  Created by Abhishek Dewan on 4/25/21.
//

import SwiftUI
import core

struct PlatformSelection: View {
    
    @ObservedObject var viewModel = PlatformSelectViewModel()
    
    var navigateForward: () -> Void
    
    init(navigateForward: @escaping () -> Void) {
        self.navigateForward = navigateForward
    }
    
    var body: some View {
        FullScreenZStack {
            if (viewModel.isLoading) {
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: Color.purple))
                    .scaleEffect(x: 2, y: 2, anchor: .center)
            } else {
                MainContent
                if (viewModel.getOwnedPlatformCount() > 0) {
                    VStack{
                        Spacer()
                        Button(action: {navigateForward()}) {
                            Text("Done")
                                .foregroundColor(.white)
                                .fontWeight(.bold)
                                .frame(minWidth: 0, maxWidth: .infinity, maxHeight: 50)
                                .background(Color.purple)
                        }.cornerRadius(10.0)
                        .padding([.leading, .trailing], 30)
                    }
                }
            }
        }
    }
    
    private var MainContent: some View {
        FullScreenVStack(alignment: HorizontalAlignment.leading) {
            Text("Platforms").font(.system(size: 34)).foregroundColor(.purple).fontWeight(.bold).padding(.leading)
            Text("Select the platforms your currently own").font(.body).foregroundColor(.white).padding(.leading)
            ScrollView {
                LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], alignment: .leading, spacing: 10, pinnedViews: [], content: {
                    ForEach(0 ..< viewModel.platformList.count) { index in
                        let platform = viewModel.platformList[index] as Platform
                        let isPlatformOwned = platform.isOwned == true
                        CircularSelectableImage(isSelected: isPlatformOwned, isSelectedColor: .purple, imageId: platform.imageId, isImageRemote: true, title: platform.name) {
                            viewModel.setPlatformAsFavorite(platform: platform, isOwned: !isPlatformOwned)
                        }
                    }
                }).padding(.all)
            }
        }
    }
}

struct PlatformProvider: PreviewProvider {
    static var previews: some View {
        let view = PlatformSelection(navigateForward: {})
        view.viewModel.isLoading = false
        view.viewModel.platformList = [
            Platform(id: 0, slug: "arcade", name: "Arcade", generation: 2, imageId: "something", isOwned: KotlinBoolean(bool: false)),
            Platform(id: 0, slug: "arcade", name: "Arcade", generation: 2, imageId: "something", isOwned: KotlinBoolean(bool: true)),
            Platform(id: 0, slug: "arcade", name: "Arcade", generation: 2, imageId: "something", isOwned: KotlinBoolean(bool: false)),
            Platform(id: 0, slug: "arcade", name: "Arcade", generation: 2, imageId: "something", isOwned: KotlinBoolean(bool: true)),
        ]
        return view
    }
}

