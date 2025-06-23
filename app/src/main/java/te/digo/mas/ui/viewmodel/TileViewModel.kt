package te.digo.mas.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import te.digo.mas.domain.model.Tile
import te.digo.mas.domain.useCase.DeleteTileUseCase
import te.digo.mas.domain.useCase.GetAllTilesUseCase
import javax.inject.Inject

@HiltViewModel
class TileViewModel @Inject constructor(
    private val getAllTilesUseCase: GetAllTilesUseCase,
    private val deleteTileUseCase: DeleteTileUseCase
) : ViewModel() {

    private val _listTiles = MutableLiveData(listOf<Tile>())
    val listTiles: LiveData<List<Tile>> = _listTiles

    init {
        fetchAllTiles()
    }

    private fun fetchAllTiles() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTilesUseCase.invoke().collect{
                _listTiles.postValue(it)
            }
        }
    }

    fun deleteTile(description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTileUseCase.invoke(description)
            fetchAllTiles()
        }
    }

}