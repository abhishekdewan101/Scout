//
//  FullScreenVStack.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/26/21.
//

import SwiftUI

struct FullScreenZStack<Content: View> : View {
    var content: () -> Content
    
    init(@ViewBuilder content: @escaping () -> Content) {
        self.content = content
    }
    
    var body: some View {
        ZStack {
            Color.black.edgesIgnoringSafeArea(.all)
            content()
        }.frame(
            minWidth: 0,
            maxWidth: .infinity,
            minHeight: 0,
            maxHeight: .infinity,
            alignment: .center
        )
    }
}

