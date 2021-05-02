//
//  TitledContainer.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/1/21.
//

import SwiftUI

struct TitledContainer<Content: View>: View {
    
    
    var title: String
    var onViewMoreClicked: () -> Void
    var content: () -> Content
    
    init(title: String, onViewMoreClicked: @escaping () -> Void, @ViewBuilder content: @escaping () -> Content) {
        self.title = title
        self.content = content
        self.onViewMoreClicked = onViewMoreClicked
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(title).font(.title).foregroundColor(Color.white)
                Spacer()
                Image(systemName: "ellipsis").rotationEffect(.degrees(-90)).onTapGesture {
                    onViewMoreClicked()
                }.foregroundColor(Color.white)
            }.padding(.leading)
            .padding(.trailing)
            content()
        }.frame(
            minWidth: 0,
            maxWidth: .infinity,
            alignment: .topLeading
        )
    }
}
