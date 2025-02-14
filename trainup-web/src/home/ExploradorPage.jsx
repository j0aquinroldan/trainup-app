import React, { useCallback, useState } from 'react';
import { obtenerRutinas } from '../api/Api';
import CardRutinaSimple from './CardRutinaSimple';
import NotRutins from '../utils/NotRutins';
import DataLoader from '../utils/DataLoader';

const ExploradorPage = () => {

  const [currentPage, setCurrentPage] = useState(0);
  const request = useCallback(() => obtenerRutinas(currentPage), [currentPage]);

  return (
    <div className='temp-content-body'>
      <DataLoader fetchData={request} >
        {(rutinas) =>
          rutinas.length > 0 ? (
            rutinas.map((rutina, index) => (
              <CardRutinaSimple key={index} rutina={rutina} />
            ))
          ) : (
            <NotRutins
              titulo="No existen rutinas todavia"
              mensaje="Nadie ha creado rutinas todavia, se el primero en crear una"
              showButton={false}
            />
          )
        }
      </DataLoader>
      <button onClick={() => setCurrentPage(currentPage + 1)}>Cargar mas</button>
      <button onClick={() => setCurrentPage(0)}>Volver al inicio</button>
    </div>
  );
};

export default ExploradorPage;