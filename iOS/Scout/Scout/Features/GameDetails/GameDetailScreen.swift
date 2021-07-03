//
//  GameDetailScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/28/21.
//

import SwiftUI
import ScoutCommon

struct GameDetailScreen: View {
    var slug: String

    @State private var viewState: GameDetailViewState = GameDetailViewState.EmptyViewState()

    // swiftlint:disable:next force_cast
    let viewModel = koin.get(objCClass: GameDetailViewModel.self) as! GameDetailViewModel

    var body: some View {
        if viewState is GameDetailViewState.EmptyViewState {
            VStack {
                ProgressView()
                    .scaleEffect(x: 2, y: 2, anchor: .center)
                    .progressViewStyle(CircularProgressViewStyle(tint: Color("White")))
                    .onAppear {
                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                            viewModel.constructGameDetails(slug: slug) {
                                viewState = $0
                            }
                        }
                    }
            }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .center)
        } else {
            // swiftlint:disable:next force_cast
            let result = viewState as! GameDetailViewState.NonEmptyViewState
            GeometryReader { geo in
                ScrollView(.vertical, showsIndicators: false) {
                    VStack {
                        GameDetailHeaderView(result: result, screenSize: geo.size)
                        GameStatsView(result: result)
                        if result.mediaList.count > 0 {
                            GameScreenshotView(result: result, screenSize: geo.size)
                        }
                        if let summary = result.summary {
                            GameDetailSummary(summary: summary)
                        }
                        if result.videoList.count > 0 {
                            GameVideoListView(result: result, screenSize: geo.size)
                        }
                    }
                    .frame(maxHeight: .infinity)
                    .padding(.bottom)
                }.frame(minWidth: 0,
                        maxWidth: .infinity,
                        minHeight: 0,
                        maxHeight: .infinity,
                        alignment: .topLeading)
            }
            .navigationBarTitle(result.name, displayMode: .inline)
            .toolbar {
                // Fix https://stackoverflow.com/questions/64405106/toolbar-is-deleting-my-back-button-in-the-navigationview/64994154#64994154
                ToolbarItem(placement: .navigationBarLeading) {Text("")}
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button {
                        print("Save Game")
                    } label: {
                        Image(systemName: "heart").font(.body)
                    }
                }
            }
        }
    }
}

struct GameDetailSummary: View {
    var summary: String

    var body: some View {
        VStack(alignment: .leading) {
            ExpandableTextField(text: summary, textColor: Color.white, font: .body)
                .frame(maxWidth: .infinity, alignment: .leading)

            Divider().padding(.top)
        }.padding(.top)
        .padding(.horizontal)
    }
}

struct GameDetailHeaderView: View {
    var result: GameDetailViewState.NonEmptyViewState
    var screenSize: CGSize

    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            if result.mediaList.count > 0 {
                let randomIndex = Int.random(in: 0...(result.mediaList.count - 1))
                ZStack {
                    AsyncImage(url: result.mediaList[randomIndex],
                               width: Int(screenSize.width),
                               height: 300,
                               contentMode: .fill
                    ).overlay(Color.black.opacity(0.4))
                }
            }
            HStack(alignment: .top) {
                AsyncImage(url: result.coverUrl,
                           width: 125,
                           height: 125,
                           contentMode: .fill,
                           cornerRadius: 15)
                VStack(alignment: .leading, spacing: 5) {
                    Text(result.name).font(.system(size: 16)).fontWeight(.bold).lineLimit(2)
                    if let developer = result.developer {
                        Text(developer.name).font(.body).foregroundColor(Color.gray)
                    }
                    Spacer()
                    Button {
                        print("Save Game")
                    } label: {
                        Text("Add to library")
                            .font(.system(size: 14))
                            .fontWeight(.bold)
                            .frame(height: 10, alignment: .center)
                            .foregroundColor(.white)
                            .padding(.all)
                            .background(Color("BrandBackground"))
                            .cornerRadius(15)
                    }.alignmentGuide(.bottom) {
                        $0[.bottom]
                    }
                }.frame(height: 125, alignment: .topLeading)
            }
            .padding(.top)
            .padding(.horizontal)
            .frame(width: screenSize.width, alignment: .leading)

            Divider()
                .padding(.top)
                .padding(.horizontal)
        }
    }
}

struct RatingView: View {
    var rating: Int

    func getRatingColor(rating: Int) -> Color {
        switch rating {
        case 75...100: return Color.green
        case 55...74: return Color.yellow
        case 0...74: return Color.red
        default: return Color.blue
        }
    }

    var body: some View {
        VStack {
            Text("Avg Rating").font(.body).fontWeight(.bold).foregroundColor(Color.gray)
            ZStack {
                Circle()
                    .fill(getRatingColor(rating: rating))
                    .frame(width: 45, height: 45)
                Text(String(rating)).font(.body).fontWeight(.bold)
            }
        }

    }
}

struct GameStatsView: View {
    var result: GameDetailViewState.NonEmptyViewState
    var body: some View {
        VStack {
            HStack(alignment: .top) {
                Spacer()
                VStack {
                    Text("Release Date")
                        .font(.body)
                        .fontWeight(.bold)
                        .foregroundColor(Color.gray)
                    Text(result.releaseDate.dateString)
                        .font(.body)
                        .foregroundColor(Color.white)
                        .padding(.top)
                }
                Spacer()
                Divider()
                if let rating = result.rating {
                    Spacer()
                    RatingView(rating: rating.intValue)
                    Spacer()
                    Divider()
                }
                Spacer()
                VStack {
                    Text(result.genres.count > 1 ? "Genres": "Genre")
                        .font(.body)
                        .fontWeight(.bold)
                        .foregroundColor(Color.gray)
                    Text(result.genres.joined(separator: "\n"))
                        .font(.body)
                        .lineLimit(2)
                        .multilineTextAlignment(.center)
                        .foregroundColor(Color.white)
                        .padding(.top)
                }
                Spacer()
            }
            Divider().padding(.horizontal)
        }.frame(height: 100)
    }
}

struct GameScreenshotView: View {
    var result: GameDetailViewState.NonEmptyViewState
    var screenSize: CGSize
    @State private var showFullScreen = false

    var body: some View {
        VStack {
            ScrollView(.horizontal, showsIndicators: false) {
                LazyHStack(spacing: 10) {
                    ForEach(result.mediaList, id: \.self) { image in
                        Button {
                            showFullScreen = true
                        } label: {
                            AsyncImage(url: image,
                                       width: Int(screenSize.width - 10),
                                       height: 200,
                                       contentMode: .fill,
                                       cornerRadius: 15)
                        }.sheet(isPresented: $showFullScreen) {
                            VStack(spacing: 0) {
                                RoundedRectangle(cornerRadius: 40)
                                    .fill(Color.white)
                                    .frame(width: 50, height: 5)
                                    .padding(.top)
                                FullScreenImageViewer(imageList: result.mediaList)
                            }

                        }
                    }
                }
            }
            Divider().padding(.horizontal)
        }.frame(height: 220)
    }
}

struct GameVideoListView: View {
    var result: GameDetailViewState.NonEmptyViewState
    var screenSize: CGSize
    var body: some View {
        VStack {
            ScrollView(.horizontal, showsIndicators: false) {
                LazyHStack(spacing: 10) {
                    ForEach(result.videoList, id: \.self) { video in
                        VideoScreenshotView(screenShotUrl: video.screenshotUrl,
                                            videoTitle: video.name,
                                            screenSize: screenSize) {
                            // swiftlint:disable:next force_unwrapping
                            let webURL = NSURL(string: video.youtubeUrl)!
                            let application = UIApplication.shared
                            application.open(webURL as URL)
                            print("Showing Video - \(video.youtubeUrl)")
                        }
                    }
                }
            }
            Divider().padding(.horizontal)
        }.frame(height: 175)
    }
}
