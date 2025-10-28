import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const API_BASE_URL = 'http://localhost:8080/api';

function App() {
  const [items, setItems] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [showAddForm, setShowAddForm] = useState(false);
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    brand: '',
    price: '',
    category: '',
    releaseDate: '',
    itemAvailable: true,
    stockQuantity: ''
  });
  const [imageFile, setImageFile] = useState(null);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/items`);
      setItems(response.data);
    } catch (error) {
      console.error('Error fetching items:', error);
    }
  };

  const handleSearch = async () => {
    if (searchTerm.trim()) {
      try {
        const response = await axios.get(`${API_BASE_URL}/items/search?keyword=${searchTerm}`);
        setItems(response.data);
      } catch (error) {
        console.error('Error searching items:', error);
      }
    } else {
      fetchItems();
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formDataObj = new FormData();
    
    const jsonData = JSON.stringify({
      name: formData.name,
      description: formData.description,
      brand: formData.brand,
      price: formData.price,
      category: formData.category,
      releaseDate: formData.releaseDate,
      itemAvailable: formData.itemAvailable,
      stockQuantity: formData.stockQuantity
    });

    const blob = new Blob([jsonData], { type: 'application/json' });
    formDataObj.append('item', blob);
    
    if (imageFile) {
      formDataObj.append('imageFile', imageFile);
    }

    try {
      await axios.post(`${API_BASE_URL}/item`, formDataObj, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      setShowAddForm(false);
      setFormData({
        name: '',
        description: '',
        brand: '',
        price: '',
        category: '',
        releaseDate: '',
        itemAvailable: true,
        stockQuantity: ''
      });
      setImageFile(null);
      fetchItems();
    } catch (error) {
      console.error('Error adding item:', error);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this item?')) {
      try {
        await axios.delete(`${API_BASE_URL}/item/${id}`);
        fetchItems();
      } catch (error) {
        console.error('Error deleting item:', error);
      }
    }
  };

  const getImageUrl = (id) => {
    return `${API_BASE_URL}/item/${id}/image`;
  };

  return (
    <div className="app">
      <header className="header">
        <h1>Product Store</h1>
        <div className="search-container">
          <input
            type="text"
            placeholder="Search items..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
            className="search-input"
          />
          <button onClick={handleSearch} className="search-btn">Search</button>
          <button onClick={() => { setShowAddForm(!showAddForm); fetchItems(); }} className="add-btn">
            {showAddForm ? 'Cancel' : 'Add New Item'}
          </button>
        </div>
      </header>

      {showAddForm && (
        <div className="form-container">
          <form onSubmit={handleSubmit} className="item-form">
            <input
              type="text"
              placeholder="Name"
              value={formData.name}
              onChange={(e) => setFormData({...formData, name: e.target.value})}
              required
            />
            <textarea
              placeholder="Description"
              value={formData.description}
              onChange={(e) => setFormData({...formData, description: e.target.value})}
              required
            />
            <input
              type="text"
              placeholder="Brand"
              value={formData.brand}
              onChange={(e) => setFormData({...formData, brand: e.target.value})}
              required
            />
            <input
              type="number"
              placeholder="Price"
              value={formData.price}
              onChange={(e) => setFormData({...formData, price: e.target.value})}
              required
            />
            <input
              type="text"
              placeholder="Category"
              value={formData.category}
              onChange={(e) => setFormData({...formData, category: e.target.value})}
              required
            />
            <input
              type="date"
              placeholder="Release Date"
              value={formData.releaseDate}
              onChange={(e) => setFormData({...formData, releaseDate: e.target.value})}
            />
            <input
              type="number"
              placeholder="Stock Quantity"
              value={formData.stockQuantity}
              onChange={(e) => setFormData({...formData, stockQuantity: e.target.value})}
              required
            />
            <input
              type="file"
              accept="image/*"
              onChange={(e) => setImageFile(e.target.files[0])}
              required
            />
            <div className="form-buttons">
              <button type="submit">Add Item</button>
            </div>
          </form>
        </div>
      )}

      <div className="items-grid">
        {items.map(item => (
          <div key={item.id} className="item-card">
            <div className="item-image">
              {item.imageName && (
                <img src={getImageUrl(item.id)} alt={item.name} />
              )}
            </div>
            <div className="item-info">
              <h3>{item.name}</h3>
              <p className="brand">{item.brand}</p>
              <p className="description">{item.description}</p>
              <p className="price">₹{item.price}</p>
              <p className="category">{item.category}</p>
              <p className="stock">Stock: {item.stockQuantity}</p>
              <div className="item-actions">
                <button onClick={() => handleDelete(item.id)} className="delete-btn">Delete</button>
              </div>
            </div>
          </div>
        ))}
      </div>

      {items.length === 0 && (
        <div className="no-items">
          <p>No items found</p>
        </div>
      )}
    </div>
  );
}

export default App;
