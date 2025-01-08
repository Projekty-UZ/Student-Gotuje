import React from 'react';
import './SingleRecipe.css'
import Navigation from '../Navigation/Navigation';

const SingleRecipe = () => {
  return (
    <div>
        <Navigation/>
        
    <div className="recipe-container">
      {/* Tytuł */}
      <header className="recipe-header">
        <h1>Przepis na naleśniki</h1>
      </header>

      {/* Lista składników */}
      <section className="ingredients-section">
        <h2>Składniki</h2>
        <ul className="ingredients-list">
          <li>1 szklanka mąki</li>
          <li>1 szklanka mleka</li>
          <li>2 jajka</li>
          <li>1 łyżka cukru</li>
          <li>1 szczypta soli</li>
          <li>1 łyżka oleju (do smażenia)</li>
        </ul>
      </section>

      {/* Instrukcja przygotowania */}
      <section className="instructions-section">
        <h2>Instrukcja przygotowania</h2>
        <ol className="instructions-list">
          <li>W misce wymieszaj mąkę, mleko, jajka, cukier i sól.</li>
          <li>Ubijaj składniki trzepaczką, aż masa będzie gładka i bez grudek.</li>
          <li>Rozgrzej patelnię na średnim ogniu i dodaj odrobinę oleju.</li>
          <li>Wlej niewielką ilość ciasta na patelnię i równomiernie rozprowadź.</li>
          <li>Smaż naleśnik z jednej strony przez około 1-2 minuty, aż będzie złoty.</li>
          <li>Przewróć naleśnik na drugą stronę i smaż jeszcze przez minutę.</li>
          <li>Powtarzaj, aż zużyjesz całe ciasto.</li>
          <li>Podawaj z ulubionymi dodatkami, np. dżemem, cukrem pudrem lub owocami.</li>
        </ol>
      </section>
    </div>
    </div>
  );
};

export default SingleRecipe;
