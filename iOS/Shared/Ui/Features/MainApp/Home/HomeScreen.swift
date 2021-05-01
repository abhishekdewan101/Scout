//
//  HomeScreen.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 4/28/21.
//

import SwiftUI
import core
import URLImage

struct HomeScreen: View {
    
    @ObservedObject var viewModel = HomeScreenViewModel()
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading){
                Text("Home")
                    .font(.system(size: 36))
                    .fontWeight(.bold)
                    .foregroundColor(Color.init("TitleColor"))
                    .padding(.leading, 20)
                    .padding(.top, 20)
                
                if (viewModel.showcaseListData.games.count > 0) {
                    let games = viewModel.showcaseListData.games
                    let imageIdList = games.filter {$0.screenShots != nil}.map {$0.screenShots![0].qualifiedUrl}
                    HorizontalImageList(imageIdList: imageIdList) { index in
                        print("Selected game was \(games[index])")
                    }
                }
            }
            .frame(
                minWidth: 0,
                maxWidth: .infinity,
                alignment: .topLeading
            )
        }
        .padding(.top)
        .navigationBarHidden(true)
        .background(Color.black).edgesIgnoringSafeArea(.top)
    }
}

struct HomeScreenPreview: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
