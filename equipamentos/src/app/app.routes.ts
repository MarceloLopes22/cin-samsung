import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { EquipamentoListaComponent } from './components/equipamento-lista/equipamento-lista.component';
import { HomeComponent } from './components/home/home.component';
import { EquipamentoNovoComponent } from './components/equipamento-novo/equipamento-novo.component';

export const ROUTES: Routes = [
    
    {path: '', component: HomeComponent},
    {path: 'equipamento-lista', component: EquipamentoListaComponent},
    {path: 'equipamento-novo', component: EquipamentoNovoComponent},
    {path: 'equipamento-novo/:id', component: EquipamentoNovoComponent},
    /*{path: 'detalhar-equipamento/:id', component: DetalharProfissionalComponent},*/
]

export const routes: ModuleWithProviders = RouterModule.forRoot(ROUTES);