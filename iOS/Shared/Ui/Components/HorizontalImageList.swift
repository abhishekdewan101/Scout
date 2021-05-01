//
// Created by Abhishek Dewan on 4/29/21.
//

import SwiftUI
import URLImage

struct HorizontalImageList: View {
    
    var imageIdList: [String]
    
    var onImageSelected: (Int) -> Void
    
    init(imageIdList:[String], onImageSelected: @escaping (Int) -> Void) {
        self.imageIdList = imageIdList
        self.onImageSelected = onImageSelected
    }
    
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 10) {
                ForEach(0 ..< imageIdList.count) { index in
                    URLImage(url: URL(string: imageIdList[index])!,content: { image in
                        image
                            .resizable()
                            .scaledToFill()
                            .frame(width: 400, height: 200)
                            .border(Color.gray.opacity(0.5), width: 0.5)
                            .cornerRadius(8)
                    }).onTapGesture {
                        onImageSelected(index)
                    }
                    
                }
            }
            .padding(.leading, 10)
            .frame(height: 200)
        }
    }
}

struct HorizontalImageListPreview: PreviewProvider {
    static var previews: some View {
        HorizontalImageList(imageIdList: ["arcade","adventure","arcade","adventure","arcade","adventure","arcade","adventure"]) { index in
            print("Index selected \(index)")
        }
    }
}
