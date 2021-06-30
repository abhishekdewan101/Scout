//
//  CustomTextField.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/30/21.
//

import Foundation
import SwiftUI

struct CustomTextField: UIViewRepresentable {

    class Coordinator: NSObject, UITextFieldDelegate {

        @Binding var text: String
        @Binding var inEditing: Bool
        @Binding var isFirstResponder: Bool
        var onSubmit: () -> Void

        init(text: Binding<String>, inEditing: Binding<Bool>, isFirstResponder: Binding<Bool>, onSubmit:@escaping () -> Void) {
            _text = text
            self.onSubmit = onSubmit
            _inEditing = inEditing
            _isFirstResponder = isFirstResponder
        }

        func textFieldDidChangeSelection(_ textField: UITextField) {
            text = textField.text ?? ""
        }

        func textFieldShouldReturn(_ textField: UITextField) -> Bool {
            isFirstResponder = false
            self.onSubmit()
            return true
        }
    }

    @Binding var text: String
    @Binding var inEditing: Bool
    @Binding var isFirstResponder: Bool
    var onSubmit: () -> Void

    func makeUIView(context: UIViewRepresentableContext<CustomTextField>) -> UITextField {
        let textField = UITextField(frame: .zero)
        textField.delegate = context.coordinator
        return textField
    }

    func makeCoordinator() -> CustomTextField.Coordinator {
        return Coordinator(text: $text, inEditing: $inEditing, isFirstResponder: $isFirstResponder, onSubmit: onSubmit)
    }

    func updateUIView(_ uiView: UITextField, context: UIViewRepresentableContext<CustomTextField>) {
        uiView.text = text
        if isFirstResponder {
            uiView.becomeFirstResponder()
        } else {
            uiView.resignFirstResponder()
        }
    }
}
