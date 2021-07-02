//
//  FullScreenImageViewer.swift
//  Scout
//
//  Created by Abhishek Dewan on 7/2/21.
//

import SwiftUI

struct FullScreenImageViewer: View {
    var imageList: [String]
    var body: some View {
        GeometryReader { geo in
            ScrollView(.horizontal, showsIndicators: false) {
                TabView {
                    ForEach(imageList, id: \.self) { image in
                        AsyncImage(url: image,
                                   width: Int(geo.size.height - 40),
                                   height: Int(geo.size.width - 20),
                                   contentMode: .fill,
                                   cornerRadius: 25)
                            .rotationEffect(Angle(degrees: 90))
                    }
                    .padding(.all, 10)
                }
                .frame(width: geo.size.width, height: geo.size.height)
                .tabViewStyle(PageTabViewStyle())
            }
        }
    }
}
