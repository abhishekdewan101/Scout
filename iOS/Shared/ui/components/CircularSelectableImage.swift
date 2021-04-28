//
//  CircularSelectableImage.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/27/21.
//

import SwiftUI
import URLImage

struct CircularSelectableImage: View {
    var isSelected: Bool
    var isSelectedColor: Color
    var imageId: String
    var isImageRemote: Bool
    var title: String
    var onSelected: () -> Void
    
    init(isSelected: Bool, isSelectedColor: Color, imageId: String, isImageRemote: Bool, title: String, onSelected: @escaping () -> Void) {
        self.isSelected = isSelected
        self.isSelectedColor = isSelectedColor
        self.imageId = imageId
        self.isImageRemote = isImageRemote
        self.title = title
        self.onSelected = onSelected
    }
    
    var body: some View {
        ZStack{
            Circle()
                .fill(Color.white)
                .frame(width: 150, height: 150)
                .padding()
                .if(isSelected) { view in
                    view.overlay(Circle().stroke(isSelectedColor, lineWidth: 5))
                }
            VStack {
                if (isImageRemote) {
                    URLImage(url: buildImageUrl(imageId: imageId)!,content: { image in
                        image
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 50, height: 50)
                    })
                } else {
                    Image(imageId)
                        .resizable()
                        .frame(width: 50, height: 50)
                }
                Text(title)
                    .foregroundColor(.black)
                    .font(.body)
                    .lineLimit(2)
                    .frame(width: 100)
            }
        }.onTapGesture {
            onSelected()
        }
    }
}

struct CircularSelectableImage_Previews: PreviewProvider {
    static var previews: some View {
        CircularSelectableImage(isSelected: true, isSelectedColor: .purple, imageId: "arcade", isImageRemote: false, title: "Arcade", onSelected: { })
            .preferredColorScheme(.dark)
    }
}
