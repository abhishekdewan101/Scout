//
//  PlatformSelectionScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/20/21.
//

import SwiftUI
import ScoutCommon

struct PlatfromGridItem: View {
    var slug: String
    var imageUrl: String
    var isSelected: Bool = false
    var onClick: (String) -> Void

    var body: some View {
        ZStack {
            Circle()
                .fill(Color("White"))
                .frame(width: 175, height: 150, alignment: .center)
                .padding()
                .if(isSelected) {
                    $0.overlay(Circle().strokeBorder(Color("White"), lineWidth: 5))
                }
            AsyncImage(url: imageUrl,
                       width: 85,
                       height: 85
            )
        }.onTapGesture {
            onClick(slug)
        }
    }
}

struct PlatformSelectionScreen: View {
    @State private var viewState: PreferenceSelectionViewState = PreferenceSelectionViewState.Loading()
    @Binding var preferenceSelectionStage: PreferenceSelectionStage

    // swiftlint:disable:next force_cast
    let viewModel = koin.get(objCClass: PreferenceSelectionViewModel.self) as! PreferenceSelectionViewModel

    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]

    var body: some View {
        if viewState is PreferenceSelectionViewState.Loading {
            ZStack {
                Color("Purple").edgesIgnoringSafeArea(.all)
                ProgressView()
                    .scaleEffect(x: 2, y: 2, anchor: .center)
                    .progressViewStyle(CircularProgressViewStyle(tint: Color("White")))
            }.onAppear {
                viewModel.getPlatforms { state in
                    viewState = state
                }
            }
        } else {
            // swiftlint:disable:next force_cast
            let result = viewState as! PreferenceSelectionViewState.Result
            ZStack(alignment: .bottom) {
                Color("Purple").edgesIgnoringSafeArea(.all)
                ScrollView {
                    VStack(alignment: .center, spacing: 5) {
                        Text("Platforms")
                            .font(.largeTitle)
                            .foregroundColor(.white)
                            .fontWeight(.bold)
                        Text("Select the platforms you own")
                            .font(.body)
                            .foregroundColor(.white)
                        LazyVGrid(columns: columns, spacing: 20) {
                            ForEach(result.platforms.indices, id: \.self) { index in
                                let platform = result.platforms[index]
                                PlatfromGridItem(slug: platform.slug,
                                                 imageUrl: platform.imageId,
                                                 // swiftlint:disable:next force_unwrapping
                                                 isSelected: platform.isOwned!.boolValue) { slug in
                                    // swiftlint:disable:next force_unwrapping
                                    viewModel.togglePlatform(platformSlug: slug, isOwned: !platform.isOwned!.boolValue)
                                }
                            }
                        }.padding()
                       Spacer(minLength: 50)
                    }
                    .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
                }
                if result.ownedPlatformCount > 0 {
                    Button {
                        preferenceSelectionStage = .genreSelection
                    } label: {
                        HStack {
                            Image(systemName: "checkmark.circle.fill")
                                .font(.title2)
                            Text("Done")
                                .fontWeight(.semibold)
                                .font(.title2)
                        }
                        .padding()
                        .foregroundColor(.white)
                        .frame(maxWidth: 300)
                        .background(Color("Red"))
                        .cornerRadius(20)
                    }
                    .buttonStyle(PlainButtonStyle())
                } else {
                    EmptyView()
                }
            }
        }
    }
}

struct PlatformSelectionScreen_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            PlatformSelectionScreen(preferenceSelectionStage: Binding.constant(.platformSelection))
            PlatformSelectionScreen(preferenceSelectionStage: Binding.constant(.platformSelection))
                .preferredColorScheme(.dark)
        }
    }
}
