//
//  FullScreenVStack.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/26/21.
//

import SwiftUI

struct FullScreenVStack<Content: View>: View {
    var content: () -> Content
    var alignment: HorizontalAlignment

    init(alignment: HorizontalAlignment, @ViewBuilder content: @escaping () -> Content) {
        self.alignment = alignment
        self.content = content
    }

    var body: some View {
        VStack(alignment: alignment, spacing: nil, content: {
          content()
        }).frame(
            minWidth: 0,
            maxWidth: .infinity,
            minHeight: 0,
            maxHeight: .infinity,
            alignment: .topLeading
        )
    }
}
