package com.connercaspar.pokemonapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitPokemonApiCalls {

    @GET("{name}")
    Call<Pokemon> getPokemonAbility(@Path("name") String pokemonName);

    class Pokemon {

        @SerializedName("effect_entries")
        private ArrayList<String> effectEntries;

        public ArrayList<String> getEffectEntries() {return effectEntries;}

        @SerializedName("front_default")
        private String pokemonImageString;

        public String getPokemonImageString() {return pokemonImageString;}

    }

}
