(ns structured-data)

(defn do-a-thing [x]
  (let [sum (+ x x)]
    (Math/pow sum sum)))

(defn spiff [v]
  (+ (v 0) (v 2)))

(defn cutify [v]
  (conj v "<3"))

(defn spiff-destructuring [v]
  (let [[a _ b] v]
    (+ a b)))

(defn point [x y]
  [x y])

(defn rectangle [bottom-left top-right]
  [bottom-left top-right])

(defn width [rectangle]
  (let [[[x1 _] [x2 _]] rectangle]
    (Math/abs (- x1 x2))))

(defn height [rectangle]
  (let [[[_ y1] [_ y2]] rectangle]
    (Math/abs (- y1 y2))))

(defn square? [rectangle]
  (let [w (width rectangle)
        h (height rectangle)]
    (= h w)))

(defn area [rectangle]
  (let [w (width rectangle)
        h (height rectangle)]
    (* h w)))

(defn contains-point? [rectangle point]
  (let [[[x1 x2] [y1 y2]] rectangle
        [px py] point]
    (and
      (<= x1 px y1)
      (<= x2 py y2))))

(defn contains-rectangle? [outer inner]
  (let [[p1 p2] inner]
    (and
      (contains-point? outer p1)
      (contains-point? outer p2))))

(defn title-length [book]
  (count (:title book)))

(defn author-count [book]
  (count (:authors book)))

(defn multiple-authors? [book]
  (> (author-count book) 1))

(defn add-author [book new-author]
  (let [auth (:authors book)]
    (assoc book :authors
      (conj auth new-author))))

(defn alive? [author]
  (not (contains? author :death-year)))

(defn element-lengths [collection]
  (map count collection))

(defn second-elements [collection]
  (let [get-element (fn [inner] (get inner 1))]
    (map get-element collection)))

(defn titles [books]
  (map :title books))

(defn monotonic? [a-seq]
  (if 
    (or 
      (apply <= a-seq)
      (apply >= a-seq))
    true
    false))

(defn stars [n]
  (apply str (repeat n "*")))

(defn toggle [a-set elem]
  (if (contains? a-set elem)
    (disj a-set elem)
    (conj a-set elem)))

(defn contains-duplicates? [a-seq]
  (not (== (count a-seq) (count (set a-seq)))))

(defn old-book->new-book [book]
  (assoc book :authors (set (:authors book))))

(defn has-author? [book author]
  (contains? (:authors book) author))

(defn authors [books]
  (let [book-author
        (fn [book] (:authors book))]
    (set (apply concat (map book-author books)))))

(defn all-author-names [books]
  (set (map :name (authors books))))
  
(defn author->string [author]
  (let [author-name (:name author)
        death-year (:death-year author)
        birth-year (:birth-year author)]
    (if (contains? author :birth-year)
      (apply str author-name " (" birth-year " - " death-year ")")
      (str author-name))))

(defn authors->string [authors]
  (apply str (interpose ", " (map author->string authors))))

(defn book->string [book]
  (apply str (:title book) ", written by " (authors->string (:authors book))))

(defn books->string [books]
  (cond 
    (empty? books) "No books."
    (= (count books) 1) (str "1 book. " (apply str (interpose ", " (map book->string books))) ".")
    :else (str (count books) " books. " (apply str (interpose ", " (map book->string books))) ".")))

(defn books-by-author [author books]
  (filter (fn [book] (has-author? book author)) books))

(defn author-by-name [name authors]
  (first (filter (fn [author] (= name (:name author))) authors)))
  
(defn living-authors [authors]
  (filter (fn [author] (alive? author)) authors))

(defn has-a-living-author? [book]
  (not (empty? (living-authors (:authors book)))))

(defn books-by-living-authors [books]
  (filter (fn [book] (has-a-living-author? book)) books))

; %________%
