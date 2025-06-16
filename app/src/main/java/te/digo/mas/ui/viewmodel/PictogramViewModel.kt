package te.digo.mas.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import te.digo.mas.R
import te.digo.mas.domain.model.Tile
import te.digo.mas.domain.repository.TeDigoMasRepository
import te.digo.mas.domain.useCase.GetAllTiles
import javax.inject.Inject

@HiltViewModel
class PictogramViewModel @Inject constructor(
    private val getAllTiles: GetAllTiles
) : ViewModel() {

    private val _listTiles = MutableLiveData(listOf<Tile>())
    val listTiles: LiveData<List<Tile>> = _listTiles

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTiles.invoke().collect{
                _listTiles.postValue(it)
            }
        }
    }

    private val _pictograms = MutableStateFlow(
        listOf(
            R.drawable.picto,
            R.drawable.picto,
            R.drawable.picto,
            R.drawable.picto,
            R.drawable.picto,
            R.drawable.picto
        )
    )
    val pictograms: StateFlow<List<Int>> = _pictograms

}