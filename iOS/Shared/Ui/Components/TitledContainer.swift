//
//  TitledContainer.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/1/21.
//

import SwiftUI
import core

struct TitledContainer<Content: View>: View {

    var title: String
    var onViewMoreClicked: () -> GameListData
    var content: () -> Content

    init(title: String, onViewMoreClicked: @escaping () -> GameListData, @ViewBuilder content: @escaping () -> Content) {
        self.title = title
        self.content = content
        self.onViewMoreClicked = onViewMoreClicked
    }

    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(title).font(.title).foregroundColor(Color.white)
                Spacer()
                NavigationLink(
                    destination: ExpandedList(listData: onViewMoreClicked()) ,
                    label: {
                        Image(systemName: "ellipsis").rotationEffect(.degrees(-90)).foregroundColor(Color.white)
                    })
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
