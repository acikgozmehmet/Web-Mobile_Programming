import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipeValue: any;
  placeValue: any;
  venueList = [];
  recipeList = [];

  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  myCollection: any;

  recipeApi = 'https://api.edamam.com/search?q=';
  recipeAppId = '&app_id=b669e7fd';
  recipeAppKey = '&app_key=e318dded957c63cab318fd1571c468e8';

  placesApi = 'https://api.foursquare.com/v2/venues/explore?';
  client_id = '&client_id=XG5YHN43ZDDTJDBIHQUS2Q0G0PRCKKPMNPI2LCD52M1ZXWT0';
  client_secret = '&client_secret=ZMIHD2S5VWLTUPNBOXH1GUDTOVQ5YRF01F3SETI3ZMZCUA0W';
  version = '&v=20180323';
  near = '&near=';
  limit = '&limit=5';



  constructor(private _http: HttpClient) {
  }

  ngOnInit() {

    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipeValue = this.recipes.nativeElement.value;
    this.placeValue = this.places.nativeElement.value;

    if (this.recipeValue !== null) {
      /**
       * Write code to get recipe
       */

      let requestRecipe = this.recipeApi + this.recipeValue + this.recipeAppId + this.recipeAppKey;
      console.log(requestRecipe);

      this._http.get(requestRecipe).subscribe(responseRecipe => {
        this.recipeList = responseRecipe['hits'];
      }, error => {});


    }

    if (this.placeValue != null && this.placeValue !== '' && this.recipeValue != null && this.recipeValue !== '') {
      /**
       * Write code to get place
       */

      const requestVenueAndRecipe = this.placesApi + this.near + this.placeValue + this.client_id + this.client_secret + this.version  +
                                   '&query=' + this.recipeValue;
                                  // this.limit + '&query=' + this.recipeValue;
      console.log(requestVenueAndRecipe);
      // console.log(encodeURIComponent(this.placeValue));
      this._http.get(requestVenueAndRecipe).subscribe(responseVenueAndRecipe => {
          this.myCollection = responseVenueAndRecipe['response'].groups[0].items;
          this.venueList = this.myCollection.map( i => i.venue);
      }, error => {});

    }
  }
}
