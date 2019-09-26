import { BrowserModule } from '@angular/platform-browser';
import { EquipamentoService } from './services/equipamento.service';
import { NgModule, LOCALE_ID } from '@angular/core';
import { NgxQRCodeModule } from 'ngx-qrcode2';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { MenuComponent } from './components/menu/menu.component';
import { FormsModule } from '@angular/forms';
import { routes } from './app.routes';
import { HttpClientModule } from '@angular/common/http';
import { EquipamentoListaComponent } from './components/equipamento-lista/equipamento-lista.component';
import { DialogService } from './services/dialog.service';
import { registerLocaleData } from '@angular/common';
import ptBr from '@angular/common/locales/pt';
import { HomeComponent } from './components/home/home.component';
import { EquipamentoNovoComponent } from './components/equipamento-novo/equipamento-novo.component';
registerLocaleData(ptBr);
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { EquipamentoDetalheComponent } from './components/equipamento-detalhe/equipamento-detalhe.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    MenuComponent,
    HomeComponent,
    EquipamentoListaComponent,
    EquipamentoNovoComponent,
    EquipamentoDetalheComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    routes,
    HttpClientModule,
    FormsModule,
    NgxQRCodeModule
  ],
  providers: [{provide: LOCALE_ID, useValue: 'pt-BR'}, EquipamentoService, DialogService],
  bootstrap: [AppComponent]
})
export class AppModule { }
