import { useEffect, useState } from "react";
import API from "../api";

export default function Dashboard({ setAuth }) {
  const [trades, setTrades] = useState([]);
  const [editingId, setEditingId] = useState(null);

  const [symbol, setSymbol] = useState("");
  const [type, setType] = useState("");
  const [quantity, setQuantity] = useState("");
  const [price, setPrice] = useState("");

  const fetchTrades = async () => {
    const res = await API.get("/trades");
    setTrades(res.data);
  };
useEffect(() => {
  const loadTrades = async () => {
    try {
      const res = await API.get("/trades");
      setTrades(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  loadTrades();
}, []);

  const resetForm = () => {
    setEditingId(null);
    setSymbol("");
    setType("");
    setQuantity("");
    setPrice("");
  };

  const handleSubmit = async () => {
    try {
      const payload = {
        assetsSymbol: symbol,   
        type,
        quantity: Number(quantity),
        price: Number(price),
      };

      if (editingId) {
        await API.put(`/trades/${editingId}`, payload);
      } else {
        await API.post("/trades", payload);
      }

      resetForm();
      fetchTrades();
    } catch (err) {
      console.error(err);
      alert("Operation failed");
    }
  };

  const handleEdit = (trade) => {
    setEditingId(trade.id);
    setSymbol(trade.assetsSymbol);
    setType(trade.type);
    setQuantity(trade.quantity);
    setPrice(trade.price);
  };

  const handleDelete = async (id) => {
    await API.delete(`/trades/${id}`);
    fetchTrades();
  };

  const logout = () => {
    localStorage.removeItem("token");
    setAuth(false);
  };

  return (
    <>
      <div className="navbar">
        PrimeTrade Dashboard
        <button className="logout" style={{ float: "right" }} onClick={logout}>
          Logout
        </button>
      </div>

      <div className="container">

        <div className="card">
          <h3>{editingId ? "Edit Trade" : "Create Trade"}</h3>

          <div className="form-row">
            <input
              placeholder="Symbol"
              value={symbol}
              onChange={(e) => setSymbol(e.target.value)}
            />
            <input
              placeholder="Type (BUY/SELL)"
              value={type}
              onChange={(e) => setType(e.target.value)}
            />
            <input
              placeholder="Quantity"
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
            />
            <input
              placeholder="Price"
              value={price}
              onChange={(e) => setPrice(e.target.value)}
            />
            <button onClick={handleSubmit}>
              {editingId ? "Update" : "Add"}
            </button>
          </div>
        </div>

        <div className="card">
          <h3>Trades</h3>

          <table>
            <thead>
              <tr>
                <th>Asset</th>
                <th>Type</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {trades.map((t) => (
                <tr key={t.id}>
                  <td>{t.assetsSymbol}</td> {/* 👈 CHANGED */}
                  <td>{t.type}</td>
                  <td>{t.quantity}</td>
                  <td>{t.price}</td>
                  <td>
                    <button
                      className="action-btn"
                      onClick={() => handleEdit(t)}
                    >
                      Edit
                    </button>
                    <button onClick={() => handleDelete(t.id)}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

        </div>
      </div>
    </>
  );
}