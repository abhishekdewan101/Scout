//
//  GamePosterGrid.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/1/21.
//

import SwiftUI
import URLImage

struct GamePosterGrid: View {

    var imageIdList: [String]

    var onImageSelected: (Int) -> String

    init(imageIdList: [String], onImageSelected: @escaping (Int) -> String) {
        self.imageIdList = imageIdList
        self.onImageSelected = onImageSelected
    }

    var body: some View {
        LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())], alignment: .leading, spacing: 10, pinnedViews: [], content: {
            ForEach(0 ..< imageIdList.count) { index in
                NavigationLink(
                    destination: GameDetailScreen(gameSlug: onImageSelected(index)),
                    label: {
                        URLImage(url: URL(string: imageIdList[index])!, content: { image in
                            image
                                .resizable()
                                .scaledToFill()
                                .frame(width: 125, height: 175)
                                .border(Color.gray.opacity(0.5), width: 0.5)
                                .cornerRadius(8)
                        })
                    })
            }
        }).padding(.all)
    }
}

struct GamePosterGrid_Previews: PreviewProvider {
    static var previews: some View {
        GamePosterGrid(imageIdList: ["arcade", "adventure", "sport", "arcade", "adventure", "sport"]) { _ in
            return ""
        }
    }
}
