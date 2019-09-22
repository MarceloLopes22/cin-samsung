import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { EquipamentoListaComponent } from './components/equipamento-lista/equipamento-lista.component';

export const ROUTES: Routes = [
    
    //{path: '', component: HomeComponent},
    {path: 'equipamento-lista', component: EquipamentoListaComponent},
    /*{path: 'equipamento-novo', component: NovoProfissionalComponent},
    {path: 'equipamento-novo/:id', component: NovoProfissionalComponent},
    {path: 'detalhar-equipamento/:id', component: DetalharProfissionalComponent},

    {path: 'list-avaliacoes', component: AvaliacoesListaComponent},
    {path: 'nova-avaliacao', component: NovaAvaliacaoComponent},
    {path: 'nova-avaliacao/:id', component: NovaAvaliacaoComponent},
    {path: 'detalhar-avaliacao/:id', component: DetalharAvaliacaoComponent}*/
]

export const routes: ModuleWithProviders = RouterModule.forRoot(ROUTES);