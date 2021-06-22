//
//  RemoteImage.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/21/21.
//

import SwiftUI

struct RemoteImage<Loading: View, Failure: View, Content: View>: View {

    @StateObject private var loader: RemoteImageLoader
    private let loading: Loading
    private let failure: Failure
    private let content: (UIImage) -> Content

    init(url: String,
         @ViewBuilder loading: @escaping () -> Loading,
         @ViewBuilder failure: @escaping () -> Failure,
         @ViewBuilder content: @escaping (UIImage) -> Content) {
        self.loading = loading()
        self.failure = failure()
        self.content = content
        self._loader = StateObject(wrappedValue: RemoteImageLoader(url: url))
    }

    var body: some View {
        switch loader.state {
        case .loading:
            return AnyView(self.loading)
        case .failure:
            return AnyView(self.failure)
        default:
            if let image = UIImage(data: loader.data) {
                return AnyView(content(image))
            } else {
                return AnyView(self.failure)
            }
        }
    }
}

struct RemoteImage_Previews: PreviewProvider {
    static var previews: some View {
        RemoteImage(url: "https://cf.geekdo-images.com/thumb/img/sD_qvrzIbvfobJj0ZDAaq-TnQPs=/fit-in/200x150/pic2649952.jpg") {
            ProgressView()
        } failure: {
            Text("Failure")
        } content: { image in
            Image(uiImage: image)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 200, height: 200, alignment: .center)
        }

    }
}
