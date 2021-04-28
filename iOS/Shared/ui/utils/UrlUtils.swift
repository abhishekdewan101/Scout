//
//  UrlUtils.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/27/21.
//

import Foundation

func buildImageUrl(imageId: String) -> URL? {
    return URL(string: "https://images.igdb.com/igdb/image/upload/t_720p/\(imageId).png")
}
