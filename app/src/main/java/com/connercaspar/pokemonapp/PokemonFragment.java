package com.connercaspar.pokemonapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.connercaspar.pokemonapp.MainActivity.POKEMON_NAME;

public class PokemonFragment extends Fragment {



    private String baseUrl = "https://pokeapi.co/api/v2/pokemon/";
    private List<String> abilityList = new ArrayList<>();
    private Retrofit retrofit;
    private RetrofitPokemonApiCalls retrofitPokemonApiCalls;
    private String pokemonName;
    private String listString;
    private String abilities;

    @BindView(R.id.pokemon_effect_textview)
    protected TextView effectText;

    @BindView(R.id.pokemon_imageview)
    protected ImageView pokemonImageView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void buildRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitPokemonApiCalls = retrofit.create(RetrofitPokemonApiCalls.class);


    }

    @Override
    public void onStart() {
        super.onStart();
        buildRetrofit();
        pokemonName = getArguments().getString(POKEMON_NAME);
        makeApiCall(pokemonName);
    }

    public static PokemonFragment newInstance() {
        Bundle args = new Bundle();

        PokemonFragment fragment = new PokemonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void makeApiCall(String pokemonName) {
        retrofitPokemonApiCalls.getPokemonAbility(pokemonName).enqueue(new Callback<RetrofitPokemonApiCalls.Pokemon>() {
            @Override
            public void onResponse(Call<RetrofitPokemonApiCalls.Pokemon> call, Response<RetrofitPokemonApiCalls.Pokemon> response) {
                if (response.isSuccessful()) {

                    showPokemonAbilities(response.body().getEffectEntries());
                    //setPokemonImage(response.body().getPokemonImageString());

                } else {
                    Toast.makeText(getContext(), "Error, try entering a valid pokemon name.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RetrofitPokemonApiCalls.Pokemon> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setPokemonImage (String pokemonImageUrl) {
        Glide.with(this)
                .load(pokemonImageUrl)
                .into(pokemonImageView);
    }

//    private String showPokemonAbilities(ArrayList<String> abilityList) {
//        abilityList.get
//
//}
