//
//  SearchScreen.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/28/21.
//

import SwiftUI

struct SearchScreen: View {
    var body: some View {
        FullScreenVStack(alignment: .leading) {
            ScrollView {
                Text("Search").font(.largeTitle).fontWeight(.bold).foregroundColor(Color.init("TitleColor")).padding(EdgeInsets(top: 15, leading: 10, bottom: 0, trailing: 0))

            }
        }
        .padding(.all)
        .navigationBarHidden(true)
        .background(Color.black).edgesIgnoringSafeArea(.top)
    }
}

struct SearchScreen_Previews: PreviewProvider {
    static var previews: some View {
        SearchScreen()
    }
}