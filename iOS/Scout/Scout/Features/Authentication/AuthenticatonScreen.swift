//
//  AuthenticatonScreen.swift
//  Scout
//
//  Created by Abhishek Dewan on 6/19/21.
//

import SwiftUI

struct AuthenticatonScreen: View {
    var body: some View {
        ZStack {
            Color("BackgroundPurple").ignoresSafeArea(.all)
            VStack {
                Image("AppIcon")
            }
        }
    }
}

struct AuthenticatonScreen_Previews: PreviewProvider {
    static var previews: some View {
        AuthenticatonScreen()
    }
}
