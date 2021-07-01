//
//  ExpandableTextField.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/30/21.
//

import SwiftUI

struct ExpandableTextField: View {
    var text: String
    @State private var isExpanded = false

    var body: some View {
        Text(text)
            .lineLimit(isExpanded ? 100 : 5)
            .onTapGesture {
                isExpanded.toggle()
            }
    }
}

struct ExpandableTextField_Previews: PreviewProvider {
    static var previews: some View {
        // swiftlint:disable line_length
        VStack {
            ExpandableTextField(text: """
    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce rhoncus risus id est molestie vehicula. Morbi nec sodales orci, eget venenatis quam. Ut non ligula ac tortor pellentesque vehicula sed in risus. Aenean elementum at libero vitae accumsan. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vitae tempor lacus. Mauris et mollis felis, condimentum rutrum magna. Praesent interdum cursus dui vitae scelerisque. Morbi id fermentum mi, sed rhoncus turpis. Fusce lacinia venenatis imperdiet. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam metus libero, tempus ac maximus vel, vehicula a urna. Nulla sed urna nunc.Nulla euismod pulvinar sapien, ut consectetur nulla vestibulum sed. Integer lectus mauris, auctor eu cursus venenatis, convallis non lectus. Duis varius dui est, nec faucibus ipsum convallis eget. Vestibulum tincidunt in purus sit amet posuere. Etiam malesuada in nulla in placerat. Integer ut est congue, sodales purus id, scelerisque enim. Cras mi nisi, pellentesque vel porttitor eu, porta at sapien. Fusce eleifend justo in turpis iaculis sagittis. Integer dictum pulvinar orci non tincidunt. Mauris non enim sit amet dolor porta malesuada a et mauris.Phasellus vitae nisl vel eros elementum suscipit non sed enim. Nulla facilisi. Vestibulum dignissim mauris et lectus vestibulum, eget elementum urna venenatis. Maecenas in velit convallis ligula aliquet maximus sit amet non sem. Suspendisse leo neque, elementum vitae ligula quis, pellentesque pretium tellus. Duis eu iaculis sapien. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Mauris suscipit metus nec quam hendrerit molestie. Suspendisse fringilla molestie scelerisque. Nullam suscipit elementum lacus, id lacinia libero rhoncus vitae. Aliquam in neque eget mauris posuere sodales. Duis porttitor orci vitae diam finibus vulputate. Fusce pulvinar mollis arcu ac ultricies. Quisque efficitur mauris ut purus consectetur hendrerit. Etiam quis ligula non leo tempus volutpat non vitae magna.
    """)
        }.frame(maxHeight: .infinity, alignment: .topLeading)

    }
}
