const baseURL = 'http://localhost:8181/api/books/';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        books: [],
    };
    this.deleteBook = this.deleteBook.bind(this);
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

  render() {
    return (
      <div>
        <h1>Booklist</h1>
        <BookTable books={this.state.books} deleteBook={this.deleteBook}/>
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

ReactDOM.render(<App />, document.getElementById('root') );	