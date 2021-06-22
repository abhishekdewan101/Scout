//
//  PlatformSelectionScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/20/21.
//

import SwiftUI

struct PlatfromGridItem: View {
    var isSelected: Bool = false
    var onClick: () -> Void

    var body: some View {
        ZStack {
            Circle()
                .fill(Color("White"))
                .frame(width: 175, height: 150, alignment: .center)
                .padding()
                .if(isSelected) {
                    $0.overlay(Circle().strokeBorder(Color("White"), lineWidth: 5))
                }
            AsyncImage(url: "https://upload.wikimedia.org/wikipedia/commons/7/7a/PS5_logo.png",
                       width: 85,
                       height: 85
            )
        }.onTapGesture {
            onClick()
        }
    }
}

struct PlatformSelectionScreen: View {
    @State var data = (1...12).map { _ in false }

    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]

    var body: some View {
        ZStack {
            Color("Purple").edgesIgnoringSafeArea(.all)
            ScrollView {
                VStack(alignment: .center, spacing: 5) {
                    Text("Platforms")
                        .font(.largeTitle)
                        .foregroundColor(.white)
                        .fontWeight(.bold)
                    Text("Select the platforms you own")
                        .font(.body)
                        .foregroundColor(.white)
                    LazyVGrid(columns: columns, spacing: 20) {
                        ForEach(data.indices, id: \.self) { index in
                            PlatfromGridItem(isSelected: data[index]) {
                                data[index] = !data[index]
                            }
                        }
                    }.padding()
                }
                .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
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
