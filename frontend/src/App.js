import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

function App() {
  const [items, setItems] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState('');
  const [newItem, setNewItem] = useState({
    name: '',
    description: '',
    price: ''
  });
  const [imageFile, setImageFile] = useState(null);
  const [imagePreview, setImagePreview] = useState(null);
  const [isSearching, setIsSearching] = useState(false);
  const [showAddForm, setShowAddForm] = useState(false);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    try {
      const response = await axios.get(`${API_URL}/items`);
      setItems(response.data);
    } catch (error) {
      console.error('Error fetching items:', error);
    }
  };

  const handleSearch = async (e) => {
    e.preventDefault();
    if (searchKeyword.trim() === '') {
      fetchItems();
      setIsSearching(false);
      return;
    }
    try {
      const response = await axios.get(`${API_URL}/items/search?keyword=${searchKeyword}`);
      setItems(response.data);
      setIsSearching(true);
    } catch (error) {
      console.error('Error searching items:', error);
    }
  };

  const handleAddItem = async (e) => {
    e.preventDefault();
    try {
      const formData = new FormData();
      formData.append('name', newItem.name);
      formData.append('description', newItem.description);
      formData.append('price', parseFloat(newItem.price));
      if (imageFile) {
        formData.append('image', imageFile);
      }
      const response = await axios.post(`${API_URL}/item`, formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });
      setItems([...items, response.data]);
      setNewItem({ name: '', description: '', price: '' });
      setImageFile(null);
      setImagePreview(null);
      setShowAddForm(false);
      alert('Item added successfully!');
    } catch (error) {
      console.error('Error adding item:', error);
      alert('Error adding item');
    }
  };

  const handleDeleteItem = async (id) => {
    if (window.confirm('Are you sure you want to delete this item?')) {
      try {
        await axios.delete(`${API_URL}/item/${id}`);
        setItems(items.filter(item => item.id !== id));
      } catch (error) {
        console.error('Error deleting item:', error);
        alert('Error deleting item');
      }
    }
  };

  const resetSearch = () => {
    setSearchKeyword('');
    fetchItems();
    setIsSearching(false);
  };

  const onSelectImage = (file) => {
    setImageFile(file);
    if (file) {
      const reader = new FileReader();
      reader.onload = e => setImagePreview(e.target.result);
      reader.readAsDataURL(file);
    } else {
      setImagePreview(null);
    }
  };

  return (
    <div className="App">
      <header className="app-header">
        <h1>🛍️ Product Store</h1>
      </header>

      <div className="container">
        <section className="search-section">
          <form onSubmit={handleSearch} className="search-form">
            <input
              type="text"
              placeholder="Search items..."
              value={searchKeyword}
              onChange={(e) => setSearchKeyword(e.target.value)}
              className="search-input"
            />
            <button type="submit" className="search-button">Search</button>
            {isSearching && (
              <button type="button" onClick={resetSearch} className="reset-button">
                Reset
              </button>
            )}
          </form>
        </section>

        <section className="add-section">
          <div className="add-header">
            <h2>Add New Item</h2>
            <button
              type="button"
              className="toggle-button"
              onClick={() => setShowAddForm(v => !v)}
            >
              {showAddForm ? 'Close' : 'Add Product'}
            </button>
          </div>

          {showAddForm && (
            <form onSubmit={handleAddItem} className="add-form" encType="multipart/form-data">
              <input
                type="text"
                placeholder="Item Name"
                value={newItem.name}
                onChange={(e) => setNewItem({ ...newItem, name: e.target.value })}
                required
              />
              <input
                type="text"
                placeholder="Description"
                value={newItem.description}
                onChange={(e) => setNewItem({ ...newItem, description: e.target.value })}
                required
              />
              <input
                type="number"
                placeholder="Price"
                value={newItem.price}
                onChange={(e) => setNewItem({ ...newItem, price: e.target.value })}
                required
                step="0.01"
                min="0"
              />
              <input
                type="file"
                accept="image/*"
                onChange={(e) => onSelectImage(e.target.files && e.target.files[0])}
              />
              {imagePreview && (
                <img src={imagePreview} alt="Preview" className="preview-image" />
              )}
              <button type="submit" className="add-button">Add Item</button>
            </form>
          )}
        </section>

        <section className="items-section">
          <h2>All Items ({items.length})</h2>
          <div className="items-grid">
            {items.map(item => (
              <div key={item.id} className="item-card">
                {item.imagePath && (
                  <img
                    className="item-image"
                    alt={item.name}
                    src={`${API_URL}/item/${item.id}/image`}
                  />
                )}
                <h3>{item.name}</h3>
                <p className="item-description">{item.description}</p>
                <p className="item-price">${item.price.toFixed(2)}</p>
                <button
                  onClick={() => handleDeleteItem(item.id)}
                  className="delete-button"
                >
                  Delete
                </button>
              </div>
            ))}
            {items.length === 0 && (
              <p className="no-items">No items found</p>
            )}
          </div>
        </section>
      </div>
    </div>
  );
}

export default App;

