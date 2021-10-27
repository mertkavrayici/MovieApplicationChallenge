package com.mertkavrayici.movieapplicationchallenge.ui.details

import com.mertkavrayici.movieapplicationchallenge.data.moviedetails.MovieDetails
import com.mertkavrayici.movieapplicationchallenge.remote.MovieInterface
import com.mertkavrayici.movieapplicationchallenge.utils.Constants
import com.mertkavrayici.movieapplicationchallenge.utils.Result
import com.mertkavrayici.movieapplicationchallenge.utils.Status
import java.lang.Exception
import javax.net.ssl.SSLEngineResult

class MovieDetailsRepository(private val movieInterface: MovieInterface) {



    suspend fun  getMovieDetails(imdbId:String) :Result<MovieDetails>{


        return  try {

            val response=movieInterface.getMovieDetails(imdbId,Constants.API_KEY)
            if(response.isSuccessful){

                Result(Status.SUCCESS,response.body(),null)

            }
            else{

                Result(Status.ERROR,null,null)
            }

        }catch (e:Exception){

         Result(Status.ERROR,null,null)
        }

    }
}