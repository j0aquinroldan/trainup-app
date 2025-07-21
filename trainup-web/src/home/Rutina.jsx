import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { useLogin } from '../context/LoginContext';
import FollowBtn from './FollowBtn';
import Loader from '../utils/Loader';
import Ejercicio from './Ejercicio';
import { Button } from 'antd';
import { obtenerRutinaPorId } from '../api/Api';

const Rutina = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { rutinaID, nombre } = location.state || {};
    const { user } = useLogin();
    const [isFollowed, setIsFollowed] = useState(false);
    const [isLoading, setIsLoading] = useState(true);
    const [listaDeEjercicios, setListaDeEjercicios] = useState([])
    const [dificultad, setDificultad] = useState('')
    const [descripcion, setDescripcion] = useState('')
    const [imgSrc, setImgSrc] = useState('')
    const [categoria, setCategoria] = useState('')
    
    useEffect(() => {
        obtenerRutinaPorId(rutinaID).then(({ data }) => {
            setListaDeEjercicios(data.ejercicios)
            setDescripcion(data.descripcion)
            setDificultad(data.dificultad)
            setImgSrc(data.img)
            setCategoria(data.categoria)
        }).catch((error) => {
            console.log(error)
        })

        if (user) {
            const sigueRutina = user.rutinasSeguidas.some(rutina => rutina.id === rutinaID);
            setIsFollowed(sigueRutina);
            setIsLoading(false);
        }

    }, [user, rutinaID]);


    if (isLoading) {
        return (
            <div className="rutinas-completadas-container">
                <Loader />
            </div>
        );
    }

    const handleCreateExercise = () => {
        navigate('/es/home/crear/ejercicio', { state: { rutinaID } }); // Pasar rutinaID al navegar
    };

    const deleteEjercicio = (ejercicio) => {
        const ejercicios = listaDeEjercicios.filter(e => e.id !== ejercicio.id);
        setListaDeEjercicios(ejercicios);
    }

    const updateEjercicio = (ejercicio) => {
        const ejercicios = listaDeEjercicios.map(e => {
            if (e.id === ejercicio.id) {
                return ejercicio;
            }
            return e;
        });
        setListaDeEjercicios(ejercicios);
    }

    console.log(imgSrc)

    return (
        <div className='rutina-header'>
            <div className="rutina-container">
                {/* <div className="rutina-info"> */}
                    {imgSrc && (
                    <img src={imgSrc} className="rutina-img" alt={`imagen de rutina ${nombre}`} />
                )}
                    <h1 className="rutina-title">{nombre}</h1>
                    <h2 className='rutina-subtitle' >{descripcion}</h2>
                    <div className="card-footer">
                        <p className='category'>{categoria}</p>
                        <p className={'category ' + `${dificultad.toLowerCase()}`}>{dificultad}</p>
                    </div>

                    {user.rol ==="ADMIN" ? (
                        <Button onClick={handleCreateExercise} type="primary">
                            Crear Ejercicio
                        </Button>
                    ) : (
                        <FollowBtn initFollow={isFollowed} rutinaID={rutinaID} />
                    )}
                {/* </div> */}

                
            </div>

            

            <div className='container-boxinfo overflow-y-auto padding-r-s flex-column'>
                <h1 className="rutina-title">Ejercicios</h1>
                {listaDeEjercicios.map(ejercicio => (
                    <Ejercicio key={ejercicio.id} updateEjercicio={updateEjercicio} deleteEjercicio={deleteEjercicio} ejercicio={ejercicio} rutinaID={rutinaID} />
                ))}
            </div>
        </div>
    );

};

export default Rutina;
