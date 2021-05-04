//
// Created by Abhishek Dewan on 4/29/21.
//

import SwiftUI
import URLImage

struct HorizontalImageList: View {

    var imageIdList: [String]

    var onImageSelected: (Int) -> String

    var imageWidth: CGFloat

    var imageHeight: CGFloat

    init(imageIdList: [String], imageWidth: CGFloat, imageHeight: CGFloat, onImageSelected: @escaping (Int) -> String) {
        self.imageIdList = imageIdList
        self.imageWidth = imageWidth
        self.imageHeight = imageHeight
        self.onImageSelected = onImageSelected
    }

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 10) {
                ForEach(0 ..< imageIdList.count) { index in
                    NavigationLink(
                        destination: GameDetailScreen(gameSlug: onImageSelected(index)),
                        label: {
                            URLImage(url: URL(string: imageIdList[index])!, content: { image in
                                                       image
                                                           .resizable()
                                                           .scaledToFill()
                                                           .frame(width: imageWidth, height: imageHeight)
                                                           .border(Color.gray.opacity(0.5), width: 0.5)
                                                           .cornerRadius(8)
                                                   })
                        })
                }
            }
            .padding(.leading, 10)
            .frame(height: 200)
        }
    }
}
