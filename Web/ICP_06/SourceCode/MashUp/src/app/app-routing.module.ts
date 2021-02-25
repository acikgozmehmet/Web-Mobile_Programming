import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {SearchRecipeComponent} from './search-recipe/search-recipe.component';
import {RegistrationComponent} from './registration/registration.component';
import {ContactComponent} from './contact/contact.component';

const appRoutes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent},
  { path: 'search-recipe', component: SearchRecipeComponent},
  { path: 'registration', component: RegistrationComponent},
  { path: 'contact', component: ContactComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
