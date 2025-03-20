import React, { useCallback, useState } from 'react';
import { obtenerRutinas } from '../api/Api';
import CardRutinaSimple from './CardRutinaSimple';
import NotRutins from '../utils/NotRutins';
import DataLoader from '../utils/DataLoader';
import { Pagination } from 'antd';

const ExploradorPage = () => {

  const [currentPage, setCurrentPage] = useState(0);
  const request = useCallback(() => obtenerRutinas(currentPage), [currentPage]);
  
  return (
    <div className='temp-content-body'>
      <DataLoader fetchData={request}>
        {( data ) => {
          const { content: rutinas = [], currentPage :backendPage, totalElements } = data || {};

          const currentPage = backendPage+1;

          return rutinas && rutinas.length > 0 ? (
            <>
              {rutinas.map((rutina, index) => (
                <CardRutinaSimple key={index} rutina={rutina} />
              ))}
              <Pagination total={totalElements} 
              current={currentPage}
              pageSize={9}
              onChange={(page)=>setCurrentPage(page-1)}
              showSizeChanger={false}
              show
              showQuickJumper={false} />
            </>
          ) : (
            <NotRutins
              titulo="No existen rutinas todavía"
              mensaje="Nadie ha creado rutinas todavía, sé el primero en crear una"
              showButton={false}
            />
          );
        }}
      </DataLoader>
      {/* <button onClick={() => setCurrentPage(currentPage + 1)}>Cargar mas</button>
      <button onClick={() => setCurrentPage(0)}>Volver al inicio</button> */}
    </div>
  );
};

export default ExploradorPage;