//
//  ViewExtensions.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/27/21.
//

import SwiftUI

extension View {
    @ViewBuilder
    func `if`<Content: View>(_ condition: Bool, content: (Self) -> Content) -> some View {
        if condition {
            content(self)
        } else {
            self
        }
    }
}
