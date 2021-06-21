//
//  PlatformSelectionScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/20/21.
//

import SwiftUI

struct PlatformSelectionScreen: View {
    var body: some View {
        ZStack {
            Color("Purple").edgesIgnoringSafeArea(.all)
            VStack(spacing: 5) {
                Text("Platforms")
                    .font(.largeTitle)
                    .foregroundColor(.white)
                    .fontWeight(.bold)
                Text("Select the platforms you own")
                    .font(.body)
                    .foregroundColor(.white)
                Spacer()
            }
        }
    }
}

struct PlatformSelectionScreen_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            PlatformSelectionScreen()
            PlatformSelectionScreen()
                .preferredColorScheme(.dark)
        }
    }
}
