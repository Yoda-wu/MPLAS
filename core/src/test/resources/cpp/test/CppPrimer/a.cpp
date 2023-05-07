int main()
{
    /**
      * testing the 3 functions
      */
    std::vector<int>* ptr_vi = dynamic_vector_generator();
    dynamic_vector_processor(ptr_vi);
    dynamic_vector_printer(ptr_vi);

    delete ptr_vi;

    return 0;
}