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

    var body: some View {
        RemoteImage(url: url) {
            ProgressView()
                .scaleEffect(x: 2, y: 2, anchor: .center)
                .progressViewStyle(CircularProgressViewStyle(tint: Color("White")))
        } failure: {
            Image(systemName: "exclamationmark.triangle.fill")
                .font(.system(size: 64.0))
        } content: { image in
            Image(uiImage: image)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: CGFloat(self.width),
                       height: CGFloat(self.height),
                       alignment: .center
                )
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
