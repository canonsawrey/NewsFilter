package com.example.premierleaguenewsfilter.mock

import com.example.premierleaguenewsfilter.dashboard.watched.PlayerItem
import com.example.premierleaguenewsfilter.dashboard.watched.SoccerPosition

val mockedPlayers = listOf(
    PlayerItem(1,"Santiago", "Cazorla", "Arsenal", SoccerPosition.MID, false),
    PlayerItem(2,"Eden", "Hazard", "Chelsea", SoccerPosition.MID, false),
    PlayerItem(3,"Harry", "Kane", "Tottenham", SoccerPosition.FWD, true),
    PlayerItem(4,"Tanguy", "N'dombele", "Tottenham", SoccerPosition.MID, false),
    PlayerItem(5,"Virgil", "van Dijk", "Liverpool", SoccerPosition.DEF, false),
    PlayerItem(6,"Mesut", "Ozil", "Arsenal", SoccerPosition.MID, true),
    PlayerItem(7,"Kevin", "de Bruyne", "Manchester City", SoccerPosition.MID, false),
    PlayerItem(8,"Callum", "Chambers", "Arsenal", SoccerPosition.DEF, false),
    PlayerItem(9,"Marcus", "Rashford", "Manchester United", SoccerPosition.FWD, false),
    PlayerItem(10,"Rui", "Patricio", "Wolverhampton", SoccerPosition.GK, true),
    PlayerItem(11,"Chris", "Smalling", "Manchester United", SoccerPosition.DEF, false)
)