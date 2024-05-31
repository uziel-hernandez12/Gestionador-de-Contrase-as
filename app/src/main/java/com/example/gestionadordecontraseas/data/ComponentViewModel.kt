package com.example.gestionadordecontraseas.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ComponentViewModel : ViewModel() {
    // MutableStateFlow para almacenar y emitir una lista mutable de Componente
    private val _componentes = MutableStateFlow<List<Componente>>(emptyList())

    // StateFlow expuesto para observar cambios en la lista de Componente
    val componentes: StateFlow<List<Componente>> = _componentes

    // Método para establecer la lista de componentes
    fun setComponentes(componentes: List<Componente>) {
        _componentes.value = componentes
    }

    // Método para actualizar la contraseña de un componente dado su ID
    fun updateComponentePassword(componenteId: String, newPassword: String) {
        _componentes.value = _componentes.value.map { componente ->
            // Si el nombre del componente coincide con el ID proporcionado, se actualiza su contraseña
            if (componente.nombre == componenteId) {
                componente.copy(contraseña = newPassword)
            } else {
                // Si no coincide, se deja el componente sin cambios
                componente
            }
        }
    }
}