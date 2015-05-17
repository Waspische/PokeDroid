package com.example.wasp.pokedex.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wasp.pokedex.model.PersistancePokemon;
import com.example.wasp.pokedex.sqlite.MySQLiteHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wasp on 23/04/2015.
 */
public class PersistancePokemonsDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_NATIONAL_ID };

    public PersistancePokemonsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public PersistancePokemon createPokemon(String name, int nationalId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_NATIONAL_ID, nationalId);
        long insertId = database.insert(MySQLiteHelper.TABLE_POKEMONS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_POKEMONS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        PersistancePokemon newPoke = cursorToPokemon(cursor);
        cursor.close();
        return newPoke;
    }

    public void deletePokemon(long id){
        // Define 'where' part of query.
        String selection = MySQLiteHelper.COLUMN_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(id) };
        // Issue SQL statement.
        database.delete(MySQLiteHelper.TABLE_POKEMONS, selection, selectionArgs);
    }

    public void deletePokemonByNayionalId(int nationalId){
        // Define 'where' part of query.
        String selection = MySQLiteHelper.COLUMN_NATIONAL_ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { String.valueOf(nationalId) };
        // Issue SQL statement.
        database.delete(MySQLiteHelper.TABLE_POKEMONS, selection, selectionArgs);
    }

    public void deleteAllPokemons(){
        database.delete(MySQLiteHelper.TABLE_POKEMONS, null, null);
    }

    public PersistancePokemon getPokemonByNationalId(int nationalId){
        PersistancePokemon pokemon = null;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_POKEMONS,
                allColumns, MySQLiteHelper.COLUMN_NATIONAL_ID + " = " + nationalId, null,
                null, null, null);
        if (cursor.moveToFirst()) {
            pokemon = cursorToPokemon(cursor);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return pokemon;
    }

    public List<PersistancePokemon> getAllPokemons() {
        List<PersistancePokemon> pokemons = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_POKEMONS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PersistancePokemon poke = cursorToPokemon(cursor);
            pokemons.add(poke);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return pokemons;
    }

    private PersistancePokemon cursorToPokemon(Cursor cursor) {
        PersistancePokemon poke = new PersistancePokemon();
        poke.setId(cursor.getLong(0));
        poke.setName(cursor.getString(1));
        poke.setNational_id(cursor.getInt(2));
        return poke;
    }

}
