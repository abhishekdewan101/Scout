//
//  GameDetailScreen.swift
//  Scout (iOS)
//
//  Created by Abhishek Dewan on 5/1/21.
//

import SwiftUI
import core
import URLImage

struct GameDetailScreen: View {

    var gameSlug: String

    @ObservedObject var viewModel = GameDetailViewModel()

    init(gameSlug: String) {
        self.gameSlug = gameSlug
        viewModel.getGameDetails(slug: gameSlug)
    }

    var body: some View {
        ZStack {
            Color.black.edgesIgnoringSafeArea(.all)
            VStack {
                if let gameDetailObject = viewModel.gameDetails {
                    GameDetailScreenMainContent(gameDetail: gameDetailObject)
                }
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
        }.navigationBarColor(.clear)
        .navigationBarItems(leading: EmptyView())
        .navigationBarTitleDisplayMode(.inline)
    }
}

struct GameDetailScreenMainContent: View {
    var gameDetail: IgdbGameDetail

    init(gameDetail: IgdbGameDetail) {
        self.gameDetail = gameDetail
        UIScrollView.appearance().backgroundColor = UIColor(Color.black.opacity(0.0))
    }

    var body: some View {

        let showcaseImage = gameDetail.artworks?[0].qualifiedUrl ?? gameDetail.screenShots?[0].qualifiedUrl

        ScrollView {
            VStack(alignment: .leading) {
                URLImage(url: URL(string: showcaseImage!)!, content: {image in
                    image
                        .resizable()
                        .scaledToFill()
                        .frame(minWidth: 0, idealWidth: .infinity, maxWidth: .infinity, minHeight: 0, idealHeight: 300, maxHeight: 300, alignment: .center)

                })

                HStack {
                    URLImage(url: URL(string: gameDetail.cover!.qualifiedUrl)!, content: {image in
                        image
                            .resizable()
                            .scaledToFill()
                            .frame(width: 125, height: 175)
                            .cornerRadius(8.0)

                    })
                    VStack(alignment: .leading) {
                        Text(gameDetail.name).foregroundColor(.white).font(.title)
                        Text((gameDetail.involvedCompanies?.filter {$0.developer}[0].company.name)!).foregroundColor(.white.opacity(0.5))
                        if gameDetail.totalRating != nil {
                            ZStack {
                                Circle().foregroundColor(gameDetail.totalRating!.doubleValue >= 75.0 ? Color.green : gameDetail.totalRating!.doubleValue > 55.0 ? Color.yellow : Color.red)
                                Text("\(gameDetail.totalRating!)").foregroundColor(.white)
                            }.frame(width: 40, height: 40, alignment: .center)
                        }
                    }.frame(minWidth: 0, idealWidth: .infinity, maxWidth: .infinity, minHeight: 0, idealHeight: 175, maxHeight: 175, alignment: .topLeading)
                }

                if gameDetail.summary != nil {
                    Text("Summary").font(.title).foregroundColor(Color.white.opacity(0.5))
                    Spacer()
                    ExpandableText(text: gameDetail.summary!)
                }

                Spacer()

                if gameDetail.storyline != nil {
                    Text("Storyline").font(.title).foregroundColor(Color.white.opacity(0.5))
                    Spacer()
                    ExpandableText(text: gameDetail.storyline!)
                }

                Spacer()

                if gameDetail.screenShots != nil {
                    Text("Screenshots").font(.title).foregroundColor(Color.white.opacity(0.5))
                    Spacer()
                    let imageIdList = gameDetail.screenShots!.map {$0.qualifiedUrl}
                    HorizontalImageList(imageIdList: imageIdList, imageWidth: 400, imageHeight: 200, onImageSelected: nil)
                }

                Spacer()
                Spacer()

            }.frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
        }.edgesIgnoringSafeArea(.top)
    }
}

struct ExpandableText: View {
    @State var isExpanded: Bool

    var text: String

    init(text: String) {
        self.text = text
        isExpanded = false
    }

    var body: some View {
        Text(text).font(.body)
            .foregroundColor(.white)
            .lineLimit(isExpanded == true ? 100: 5)
            .onTapGesture {isExpanded = !isExpanded }
    }
}
