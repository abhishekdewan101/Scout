//
//  VideoScreenshotView.swift
//  Scout
//
//  Created by Abhishek Dewan on 7/2/21.
//

import SwiftUI

struct VideoScreenshotView: View {
    var screenShotUrl: String
    var videoTitle: String
    var screenSize: CGSize
    var onTapped: () -> Void

    var body: some View {
        Button {
            onTapped()
        } label: {
            ZStack(alignment: .top) {
                AsyncImage(url: screenShotUrl,
                           width: Int(screenSize.width - 50),
                           height: 150,
                           contentMode: .fill)
                VStack {
                    Spacer()
                    Image(systemName: "play")
                        .font(.title2)
                        .foregroundColor(Color.white)
                        .padding()
                        .background(Color.black.opacity(0.5).clipShape(Circle()))
                    Spacer()
                    Text(videoTitle)
                        .font(.body)
                        .fontWeight(.semibold)
                        .lineLimit(1)
                        .padding(.all, 5)
                        .frame(maxWidth: .infinity)
                        .background(Color.black.opacity(0.5))
                }
            }
            .background(Color.blue)
            .cornerRadius(20)
            .padding(.horizontal, 25)
            .frame(height: 150, alignment: .center)
        }
    }
}
