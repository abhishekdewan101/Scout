//
//  ExpandableTextField.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/30/21.
//

import SwiftUI

struct ExpandableTextField: View {
    var text: String
    var textColor: Color
    var font: Font

    @State private var isExpanded = false

    var body: some View {
        Text(text)
            .foregroundColor(textColor)
            .font(font)
            .lineLimit(isExpanded ? 100 : 3)
            .onTapGesture {
                isExpanded = true
            }
    }
}
