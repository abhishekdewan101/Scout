//
//  AsyncImage.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/21/21.
//

import SwiftUI

struct AsyncImage: View {
    var url: String
    var width: Int
    var height: Int
    var contentMode: ContentMode = .fit
    var cornerRadius: Float = 0

    var body: some View {
        RemoteImage(url: url) {
            ZStack {
                ProgressView()
                    .scaleEffect(x: 2, y: 2, anchor: .center)
                    .progressViewStyle(CircularProgressViewStyle(tint: Color("White")))
            }.frame(width: CGFloat(width), height: CGFloat(height), alignment: .center)
        } failure: {
            Image(systemName: "exclamationmark.triangle.fill")
                .font(.system(size: 64.0))
        } content: { image in
            Image(uiImage: image)
                .resizable()
                .aspectRatio(contentMode: self.contentMode)
                .frame(width: CGFloat(self.width),
                       height: CGFloat(self.height),
                       alignment: .center
                )
                .cornerRadius(CGFloat(cornerRadius))
        }
    }
}

struct AsyncImage_Previews: PreviewProvider {
    static var previews: some View {
        AsyncImage(
            url: "https://upload.wikimedia.org/wikipedia/commons/7/7a/PS5_logo.png",
            width: 85,
            height: 85
        )
    }
}
