package com.example.wasp.pokedex.util;

import com.example.wasp.pokedex.provider.model.Move;

import java.util.Comparator;

/**
 * Created by Wasp on 12/05/2015.
 */
public class MoveComparator implements Comparator<Move> {

    @Override
    public int compare(Move move1, Move move2) {
        return move1.getLevel() - move2.getLevel();
    }
}
