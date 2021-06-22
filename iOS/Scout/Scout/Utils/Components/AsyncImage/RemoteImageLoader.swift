//
//  RemoteImageLoader.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/21/21.
//

import Foundation

enum LoadState {
    case loading, success, failure
}

class RemoteImageLoader: ObservableObject {
    var data = Data()
    var state = LoadState.loading

    init(url: String) {
        guard let parsedURL = URL(string: url) else {
            fatalError("Invalid URL: \(url)")
        }

        URLSession.shared.dataTask(with: parsedURL) { data, _, _ in
            if let data = data, data.count > 0 {
                self.data = data
                self.state = .success
            } else {
                self.state = .failure
            }

            DispatchQueue.main.async {
                self.objectWillChange.send()
            }
        }.resume()
    }
}
