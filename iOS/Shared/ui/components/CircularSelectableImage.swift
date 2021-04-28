//
//  CircularSelectableImage.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/27/21.
//

import SwiftUI

struct CircularSelectableImage: View {
    var isSelected: Bool
    var isSelectedColor: Color
    var imageId: String
    var title: String
    var onSelected: () -> Void
    
    init(isSelected: Bool, isSelectedColor: Color, imageId: String, title: String, onSelected: @escaping () -> Void) {
        self.isSelected = isSelected
        self.isSelectedColor = isSelectedColor
        self.imageId = imageId
        self.title = title
        self.onSelected = onSelected
    }
    
    var body: some View {
        ZStack{
            Circle()
                .fill(Color.white)
                .frame(width: 150, height: 150)
                .padding()
                .overlay(
                       Circle().stroke(isSelectedColor, lineWidth: 5)
                   )
            VStack {
                Image(imageId)
                    .resizable()
                    .frame(width: 50, height: 50)
                
                Text(title)
                    .foregroundColor(.black)
                    .font(.title2)
                    .lineLimit(2)
                    .frame(width: 100, height: .infinity, alignment: .center)
            }
        }.onTapGesture {
            onSelected()
        }
    }
}

struct CircularSelectableImage_Previews: PreviewProvider {
    static var previews: some View {
        CircularSelectableImage(isSelected: true, isSelectedColor: .purple, imageId: "arcade", title: "Arcade", onSelected: { })
            .preferredColorScheme(.dark)
    }
}
