import "./ProductPolicies.scss";

function ProductPolicies({ producto }) {
  const normas = producto.normas?.split(".");
  const saludYseguridad = producto.saludYseguridad?.split(".");
  const cancelacion = producto.cancelacion?.split(".");
  return (
    <section className="policy container-main">
      <h2 className="section-title">¿Qué tenés que saber?</h2>
      <hr className="section-divider" />
      <div className="policy__items">
        <div className="item">
          <h4>Normas de la casa</h4>
          {normas?.map((elemento, i) => (
            <p key={i}>{elemento}</p>
          ))}
        </div>
        <div className="item">
          <h4>Salud y seguridad</h4>
          {saludYseguridad?.map((elemento, i) => (
            <p key={i}>{elemento}</p>
          ))}
        </div>
        <div className="item">
          <h4>Política de cancelación</h4>
          {cancelacion?.map((elemento, i) => (
            <p key={i}>{elemento}</p>
          ))}
        </div>
      </div>
    </section>
  );
}

export default ProductPolicies;
