const baseURL = 'http://localhost:8181/api/books/';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        books: [],
    };
    this.deleteBook = this.deleteBook.bind(this);
    this.addBook = this.addBook.bind(this);
   }

  loadBooksFromServer() {
    fetch(baseURL, {
      credentials: 'same-origin'
    })
    .then((response) => response.json()) 
    .then((responseData) => { 
      console.log(responseData);
      this.setState({ 
          books: responseData._embedded.books, 
      }); 
    });
  }

  componentDidMount() {
    this.loadBooksFromServer();
  }

  deleteBook(book) {
    fetch(book._links.self.href, {
      method: 'DELETE',
      credentials: 'same-origin'
    }).then( 
        res => this.loadBooksFromServer()
    ).then(() => { 
      console.log("book deleted");
    }).catch( err => console.error(err))                
  }
  
  addBook(book) {
    fetch(baseURL, {
      method: 'POST',
      credentials: 'same-origin',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(book)
    }).then( 
        res => this.loadBooksFromServer()
    ).catch( err => console.error(err))                
  }

  render() {
    return (
      <div>
        <h1>Booklist</h1>
        <BookTable books={this.state.books} deleteBook={this.deleteBook} />
        <BookForm addBook={this.addBook}/>
      </div>
    );
  }
}

class BookTable extends React.Component {
  constructor(props) {
    super(props);    
  }

  render() {
    var books = this.props.books.map((book, index) => 
      <Book key={index} book={book} deleteBook={this.props.deleteBook} />
    );
    
    return (
      <table className="table table-hover">
        <thead>
          <tr>
            <th>Title</th>
            <th>Author</th>
            <th>Year</th>
            <th>ISBN</th>
            <th>Price</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {books}
        </tbody>
      </table>
    );
  }
}

class Book extends React.Component {
  constructor(props) {
    super(props);
    this.deleteBook = this.deleteBook.bind(this); 
  }
  
  deleteBook() {
        this.props.deleteBook(this.props.book);
  }
  
  render() {
    return (
      <tr>
        <td>{this.props.book.title}</td>
        <td>{this.props.book.author}</td>
        <td>{this.props.book.year}</td>
        <td>{this.props.book.isbn}</td>
        <td>{this.props.book.price}</td>
        <td><button className="btn btn-danger" onClick={this.deleteBook}>Remove</button></td>
      </tr>
    );
  }
}

class BookForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          title: '',
          author: '',
          year: '',
          isbn: '',
          price: ''
        };
        this.handleSubmit = this.handleSubmit.bind(this);   
        this.handleChange = this.handleChange.bind(this); 
        this.state = { open: false };    
    }
    
    toggle() {
      this.setState({
        open: !this.state.open
      });
    }
    
    handleChange(event) {
        this.setState(
            {[event.target.name]: event.target.value}
        );
    }    
    
    handleSubmit(event) {
        event.preventDefault();
        console.log("Title: " + this.state.title);
        var newBook = {
          title: this.state.title,
          author: this.state.author,
          year: parseInt(this.state.year),
          isbn: this.state.isbn,
          price: parseFloat(this.state.price)
        };
        this.props.addBook(newBook); 
    }
    
    render() {
        return (
        <div>
          <button className="btn btn-info" onClick={this.toggle.bind(this)}>Add book</button>
          <div id="bookForm" style={{"max-width":600}} className={"collapse" + (this.state.open ? ' in' : '')}>
            <div className="panel-heading"><h2>Add book</h2></div>
            <div className="panel-body">
            <form className="form">
              <input type="text" placeholder="Title" className="form-control"  name="title" onChange={this.handleChange}/>    
              <input type="text" placeholder="Author" className="form-control" name="author" onChange={this.handleChange}/>
              <input type="text" placeholder="Year" className="form-control" name="year" onChange={this.handleChange}/>
              <input type="text" placeholder="ISBN" className="form-control" name="isbn" onChange={this.handleChange}/>
              <input type="text" placeholder="Price" className="form-control" name="price" onChange={this.handleChange}/>
              <button className="btn btn-primary" onClick={this.handleSubmit}>Save</button>        
            </form>
            </div>
            </div>  
          </div>  
        );
    }
}

ReactDOM.render(<App />, document.getElementById('root') );	
